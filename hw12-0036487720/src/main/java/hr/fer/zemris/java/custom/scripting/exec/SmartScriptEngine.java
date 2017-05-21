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
	};

	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	protected void calculateFunction(ElementFunction function, Stack<Object> stack) {
		switch (function.getName()) {
		case "sin":
			ValueWrapper argument = (ValueWrapper) stack.pop();
			ValueWrapper result = new ValueWrapper(Math.sin(Double.parseDouble(argument.getValue().toString())));
			stack.push(result);
			break;
		case "decfmt":
			DecimalFormat decFormat = new DecimalFormat(stack.pop().toString());
			argument = (ValueWrapper) stack.pop();
			result = new ValueWrapper(decFormat.format(Double.parseDouble(argument.getValue().toString())));
			stack.push(result);
			break;
		case "dup":
			stack.push(stack.peek());
			break;
		case "swap":
			stack.push(stack.get(stack.size() - 2));
			stack.remove(stack.size() - 3);
			break;
		case "setMimeType":
			requestContext.setMimeType(stack.pop().toString());
			break;
		case "paramGet":
			String defValue = stack.pop().toString();
			String name = stack.pop().toString();
			String value = requestContext.getParameter(name);
			stack.push(value == null ? defValue : value);
			break;
		case "pparamGet":
			defValue = stack.pop().toString();
			name = stack.pop().toString();
			value = requestContext.getPersistentParameter(name);
			stack.push(value == null ? defValue : value);
			break;
		case "pparamSet":
			name = stack.pop().toString();
			value = stack.pop().toString();
			requestContext.setPersistentParameter(name, value);
			break;
		case "pparamDel":
			name = stack.pop().toString();
			requestContext.removePersistentParameter(name);
			break;
		case "tparamGet":
			defValue = stack.pop().toString();
			name = stack.pop().toString();
			value = requestContext.getTemporaryParameter(name);
			stack.push(value == null ? defValue : value);
			break;
		case "tparamSet":
			name = stack.pop().toString();
			value = stack.pop().toString();
			requestContext.setTemporaryParameter(name, value);
			break;
		case "tparamDel":
			name = stack.pop().toString();
			requestContext.removeTemporaryParameter(name);
			break;
		default:
			throw new IllegalArgumentException("Illegal function");
		}
	}

	protected void calculateOperator(ElementOperator operator, Stack<Object> stack) {
		ValueWrapper secondArgument = new ValueWrapper(stack.pop());
		ValueWrapper firstArgument = new ValueWrapper(stack.pop());
		switch (operator.getSymbol()) {
		case "+":
			firstArgument.add(secondArgument);
			stack.push(firstArgument);
			break;
		case "-":
			firstArgument.subtract(secondArgument);
			stack.push(firstArgument);
			break;
		case "*":
			firstArgument.multiply(secondArgument);
			stack.push(firstArgument);
			break;
		case "/":
			firstArgument.divide(secondArgument);
			stack.push(firstArgument);
			break;
		default:
			throw new IllegalArgumentException("Illegal operator");
		}
	}

	public void execute() {
		documentNode.accept(visitor);
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Ne valja"); // TODO:
			return;
		}
		String filepath = args[0];
		/*- String filepath = "D:\\Java\\workspace\\zadace\\hw03-0036487720\\example\\primjer3.txt";*/
		String documentBody = null;

		try {
			documentBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// put some parameter into parameters map
		parameters.put("broj", "4");
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();

	}

}
