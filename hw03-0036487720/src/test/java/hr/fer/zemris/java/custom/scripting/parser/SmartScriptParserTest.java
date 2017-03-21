package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	@Test(expected=SmartScriptLexerException.class)
    public void escapeWrongCharacterInText() {
        @SuppressWarnings("unused")
        SmartScriptParser parser = new SmartScriptParser("This is sample text.\\n"
                + "{$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. "
                + "{$END$}{$FOR i 0 10.2 2 $}sin("
                + "{$=i$}^2) = "
                + "{$= i i * @sin \"0.000\" @decfmt $} {$END$}");
       
    }
    @Test(expected=SmartScriptLexerException.class)
    public void escapeWrongCharacterInTag() {
        @SuppressWarnings("unused")
        SmartScriptParser parser = new SmartScriptParser("This is sample text.\\n"
                + "{$ FOR i \\{1 10 1 $} This is {$= i $}-th time this message is generated. ");
       
    }
   
    @Test(expected=SmartScriptParserException.class)
    public void moreEndTags() {
        SmartScriptParser parser = new SmartScriptParser("This is sample text.\n"
                + " This is {$= i $}-th time this message is generated. "
                + "{$END$}{$FOR i 0 10.2 2 $}sin("
                + "{$=i$}^2) = "
                + "{$= i i * @sin \"0.000\" @decfmt $} {$END$}");
       
    }
   
    @Test
    public void checkFirstTextChildOfDocument(){
        SmartScriptParser parser = new SmartScriptParser("This"
                + "{$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. "
                + "{$END$}{$FOR i 0 10.2 2 $}sin("
                + "{$=i$}^2) = "
                + "{$= i i * @sin \"0.000\" @decfmt $} {$END$}");
        DocumentNode docNode= parser.getDocumentNode();
        TextNode node = (TextNode)docNode.getChild(0);
        assertEquals("This", node.toString());
    }
   
    @Test
    public void checkIfSecondChildOfDocumentIsForLoopNode(){
        SmartScriptParser parser = new SmartScriptParser("This"
                + "{$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. "
                + "{$END$}{$FOR i 0 10.2 2 $}sin("
                + "{$=i$}^2) = "
                + "{$= i i * @sin \"0.000\" @decfmt $} {$END$}");
        DocumentNode docNode= parser.getDocumentNode();
        Node node = docNode.getChild(1);
        assertTrue(node instanceof ForLoopNode);   
    }
    @Test
    public void checkNumbersOfChild(){
        SmartScriptParser parser = new SmartScriptParser("This"
                + "{$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. "
                + "{$END$}{$FOR i 0 10.2 2 $}sin("
                + "{$=i$}^2) = "
                + "{$= i i * @sin \"0.000\" @decfmt $} {$END$}");
        DocumentNode docNode= parser.getDocumentNode();
        assertEquals(3, docNode.numberOfChildren());
    }

}
