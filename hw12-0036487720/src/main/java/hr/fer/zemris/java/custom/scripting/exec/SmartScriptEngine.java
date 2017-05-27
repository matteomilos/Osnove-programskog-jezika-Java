package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class used as an engine for execution smart script programs. In
 * {@link #execute()} method result is output on the given
 * {@link RequestContext} instance's output stream.
 * 
 * @author Matteo Miloš
 *
 */
public class SmartScriptEngine {

	/** Node representing main node. */
	private DocumentNode documentNode;

	/** Request context of this engine. */
	private RequestContext requestContext;

	/** Stack used for work with variables during execution. */
	private ObjectMultistack multistack = new ObjectMultistack();

	/** Implementation of the {@link INodeVisitor} interface. */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {

			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().getName();
			String step = node.getStepExpression().asText();
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			String end = node.getEndExpression().asText();

			multistack.push(variable, new ValueWrapper(start));

			while (start.numCompare(end) <= 0) {

				for (int i = 0, childrenNum = node.getChildren().size(); i < childrenNum; i++) {
					node.getChild(i).accept(visitor);
				}

				start.add(step);

			}
			multistack.pop(variable);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();

			for (Element token : node.getElements()) {

				if (token instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) token).getValue());
				}

				if (token instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) token).getValue());
				}

				if (token instanceof ElementString) {
					stack.push(((ElementString) token).getValue());
				}

				if (token instanceof ElementVariable) {
					ValueWrapper variable = multistack.peek(((ElementVariable) token).getName());
					stack.push(variable.getValue());
				}

				if (token instanceof ElementOperator) {
					calculateOperator((ElementOperator) token, stack);
				}

				if (token instanceof ElementFunction) {
					calculateFunction((ElementFunction) token, stack);
				}
			}

			for (Object object : stack) {

				try {
					String write = object.toString();
					requestContext.write(write);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, childrenNum = node.getChildren().size(); i < childrenNum; i++) {
				node.getChild(i).accept(this);
			}
		}

		/**
		 * Method used for calculating the result of the function upon stack
		 * parameters.
		 * 
		 * @param function
		 *            function called
		 * @param stack
		 *            stack with parameters
		 */
		private void calculateFunction(ElementFunction function, Stack<Object> stack) {

			switch (function.getName()) {

			case "sin":
				ValueWrapper argument = (ValueWrapper) stack.pop();
				ValueWrapper result = new ValueWrapper(Math.sin(Math.toRadians(Double.valueOf(argument.getValue().toString()))));
				stack.push(result);
				break;

			case "decfmt":
				DecimalFormat decFormat = new DecimalFormat(stack.pop().toString());
				argument = (ValueWrapper) stack.pop();
				result = new ValueWrapper(decFormat.format(Double.valueOf(argument.getValue().toString())));
				stack.push(result);
				break;

			case "dup":
				stack.push(stack.peek());
				break;

			case "swap":
				/*-pushes penultimate, then removes it (but at that time it is third from behind)*/
				stack.push(stack.get(stack.size() - 2));
				stack.remove(stack.size() - 3);
				break;

			case "setMimeType":
				requestContext.setMimeType(stack.pop().toString());
				break;

			case "paramGet":
				paramGetter(stack, (name) -> requestContext.getParameter(name));
				break;

			case "pparamGet":
				paramGetter(stack, (name) -> requestContext.getPersistentParameter(name));
				break;

			case "pparamSet":
				paramSetter(stack, (name, value) -> requestContext.setPersistentParameter(name, value));
				break;

			case "pparamDel":
				paramDeleter(stack, (name) -> requestContext.removePersistentParameter(name));
				break;

			case "tparamGet":
				paramGetter(stack, (name) -> requestContext.getTemporaryParameter(name));
				break;
			case "tparamSet":
				paramSetter(stack, (name, value) -> requestContext.setTemporaryParameter(name, value));
				break;

			case "tparamDel":
				paramDeleter(stack, (name) -> requestContext.removeTemporaryParameter(name));
				break;

			default:
				throw new IllegalArgumentException("Illegal function");
			}
		}

		/**
		 * Method used for deleting parameter using given consumer.
		 * 
		 * @param stack
		 *            stack with parameters
		 * @param deleter
		 *            deletes parameter from map given in consumer
		 */
		private void paramDeleter(Stack<Object> stack, Consumer<String> deleter) {
			String name = stack.pop().toString();
			deleter.accept(name);
		}

		/**
		 * Method used for getting parameter using given function.
		 * 
		 * @param stack
		 *            stack with parameters
		 * @param getter
		 *            gets parameter from map given in function
		 */
		private void paramGetter(Stack<Object> stack, Function<String, String> getter) {
			String defValue = stack.pop().toString();
			String name = stack.pop().toString();
			String value = getter.apply(name);
			stack.push(
					value == null	? defValue
									: value
			);
		}

		/**
		 * Method used for deleting parameter using given biconsumer.
		 * 
		 * @param stack
		 *            stack with parameters
		 * @param setter
		 *            put parameter in map given in biconsumer
		 */
		private void paramSetter(Stack<Object> stack, BiConsumer<String, String> setter) {
			String name = stack.pop().toString();
			String value = stack.pop().toString();
			setter.accept(name, value);
		}

		/**
		 * Method used for calculating the result of the operation and pushing
		 * it on the stack.
		 * 
		 * @param operator
		 *            operator of the operation
		 * @param stack
		 *            stack with parameters
		 */
		private void calculateOperator(ElementOperator operator, Stack<Object> stack) {
			ValueWrapper secondArgument = new ValueWrapper(stack.pop());
			ValueWrapper firstArgument = new ValueWrapper(stack.pop());

			switch (operator.getSymbol()) {
			
			case "+":
				firstArgument.add(secondArgument);
				break;

			case "-":
				firstArgument.subtract(secondArgument);
				break;

			case "*":
				firstArgument.multiply(secondArgument);
				break;

			case "/":
				firstArgument.divide(secondArgument);
				break;

			default:
				throw new IllegalArgumentException("Illegal operator");
			}
			stack.push(firstArgument);
		}
	};

	/**
	 * Public constructor for instancing new {@link SmartScriptEngine}.
	 * 
	 * @param documentNode
	 *            base node of the engine
	 * @param requestContext
	 *            request context of the engine
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Method used for execution of this engine.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
