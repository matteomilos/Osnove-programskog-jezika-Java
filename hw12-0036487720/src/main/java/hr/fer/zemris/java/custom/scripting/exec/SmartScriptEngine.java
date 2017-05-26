package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartScriptEngine {

	private DocumentNode documentNode;

	private RequestContext requestContext;

	private ObjectMultistack multistack = new ObjectMultistack();

	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(
				TextNode node
		) {

			try {
				requestContext
						.write(
								node.getText()
						);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(
						0
				);
			}
		}

		@Override
		public void visitForLoopNode(
				ForLoopNode node
		) {
			String variable = node
					.getVariable()
					.getName();
			String step = node
					.getStepExpression()
					.asText();
			ValueWrapper start = new ValueWrapper(
					node.getStartExpression()
							.asText()
			);
			String end = node
					.getEndExpression()
					.asText();

			multistack
					.push(
							variable,
							new ValueWrapper(
									start
							)
					);

			while (start
					.numCompare(
							end
					) <= 0) {

				for (int i = 0, childrenNum = node
						.getChildren()
						.size(); i < childrenNum; i++) {
					node.getChild(
							i
					).accept(
							visitor
					);
				}

				start.add(
						step
				);

			}
			multistack
					.pop(variable);
		}

		@Override
		public void visitEchoNode(
				EchoNode node
		) {
			Stack<Object> stack = new Stack<>();

			for (Element token : node
					.getElements()) {

				if (token instanceof ElementConstantDouble) {
					stack.push(
							((ElementConstantDouble) token)
									.getValue()
					);
				}

				if (token instanceof ElementConstantInteger) {
					stack.push(
							((ElementConstantInteger) token)
									.getValue()
					);
				}

				if (token instanceof ElementString) {
					stack.push(
							((ElementString) token)
									.getValue()
					);
				}

				if (token instanceof ElementVariable) {
					ValueWrapper variable = multistack
							.peek(
									((ElementVariable) token)
											.getName()
							);
					stack.push(
							variable.getValue()
					);
				}

				if (token instanceof ElementOperator) {
					calculateOperator(
							(ElementOperator) token,
							stack
					);
				}

				if (token instanceof ElementFunction) {
					calculateFunction(
							(ElementFunction) token,
							stack
					);
				}
			}

			for (Object object : stack) {

				try {
					String write = object
							.toString();
					requestContext
							.write(write);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void visitDocumentNode(
				DocumentNode node
		) {
			for (int i = 0, childrenNum = node
					.getChildren()
					.size(); i < childrenNum; i++) {
				node.getChild(
						i
				).accept(
						this
				);
			}
		}
	};

	public SmartScriptEngine(
			DocumentNode documentNode,
			RequestContext requestContext
	) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	protected void calculateFunction(
			ElementFunction function,
			Stack<Object> stack
	) {

		switch (function
				.getName()) {

		case "sin":
			ValueWrapper argument = (ValueWrapper) stack
					.pop();
			ValueWrapper result = new ValueWrapper(
					Math.sin(
							Math.toRadians(
									Double.valueOf(
											argument.getValue()
													.toString()
									)
							)
					)
			);
			stack.push(
					result
			);
			break;

		case "decfmt":
			DecimalFormat decFormat = new DecimalFormat(
					stack.pop()
							.toString()
			);
			argument = (ValueWrapper) stack
					.pop();
			result = new ValueWrapper(
					decFormat
							.format(
									Double.valueOf(
											argument.getValue()
													.toString()
									)
							)
			);
			stack.push(
					result
			);
			break;

		case "dup":
			stack.push(
					stack.peek()
			);
			break;

		case "swap":
			/*-pushes penultimate, then removes it (but at that time it is third from behind)*/
			stack.push(
					stack.get(
							stack.size()
									- 2
					)
			);
			stack.remove(
					stack.size()
							- 3
			);
			break;

		case "setMimeType":
			requestContext
					.setMimeType(
							stack.pop()
									.toString()
					);
			break;

		case "paramGet":
			paramGetter(
					stack,
					(name) -> requestContext
							.getParameter(
									name
							)
			);
			break;

		case "pparamGet":
			paramGetter(
					stack,
					(name) -> requestContext
							.getPersistentParameter(
									name
							)
			);
			break;

		case "pparamSet":
			paramSetter(
					stack,
					(name, value) -> requestContext
							.setPersistentParameter(
									name,
									value
							)
			);
			break;

		case "pparamDel":
			paramDeleter(
					stack,
					(name) -> requestContext
							.removePersistentParameter(
									name
							)
			);
			break;

		case "tparamGet":
			paramGetter(
					stack,
					(name) -> requestContext
							.getTemporaryParameter(
									name
							)
			);
			break;
		case "tparamSet":
			paramSetter(
					stack,
					(name, value) -> requestContext
							.setTemporaryParameter(
									name,
									value
							)
			);
			break;

		case "tparamDel":
			paramDeleter(
					stack,
					(name) -> requestContext
							.removeTemporaryParameter(
									name
							)
			);
			break;

		default:
			throw new IllegalArgumentException(
					"Illegal function"
			);
		}
	}

	private void paramDeleter(
			Stack<Object> stack,
			Consumer<String> deleter
	) {
		String name = stack
				.pop()
				.toString();
		deleter.accept(
				name
		);
	}

	private void paramGetter(
			Stack<Object> stack,
			Function<String, String> getter
	) {
		String defValue = stack
				.pop()
				.toString();
		String name = stack
				.pop()
				.toString();
		String value = getter
				.apply(name);
		stack.push(
				value == null	? defValue
								: value
		);
	}

	private void paramSetter(
			Stack<Object> stack,
			BiConsumer<String, String> setter
	) {
		String name = stack
				.pop()
				.toString();
		String value = stack
				.pop()
				.toString();
		setter.accept(
				name,
				value
		);
	}

	protected void calculateOperator(
			ElementOperator operator,
			Stack<Object> stack
	) {
		ValueWrapper secondArgument = new ValueWrapper(
				stack.pop()
		);
		ValueWrapper firstArgument = new ValueWrapper(
				stack.pop()
		);

		switch (operator
				.getSymbol()) {
		case "+":
			firstArgument
					.add(secondArgument);
			stack.push(
					firstArgument
			);
			break;

		case "-":
			firstArgument
					.subtract(
							secondArgument
					);
			stack.push(
					firstArgument
			);
			break;

		case "*":
			firstArgument
					.multiply(
							secondArgument
					);
			stack.push(
					firstArgument
			);
			break;

		case "/":
			firstArgument
					.divide(secondArgument);
			stack.push(
					firstArgument
			);
			break;

		default:
			throw new IllegalArgumentException(
					"Illegal operator"
			);
		}
	}

	public void execute() {
		documentNode
				.accept(visitor);
	}

}
