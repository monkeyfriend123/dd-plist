import com.dd.plist.*;
import java.io.File;
import junit.framework.TestCase;

public class IssueTest extends TestCase {
    public static void testIssue4() throws Exception {
        NSDictionary d = (NSDictionary)PropertyListParser.parse(new File("test/issue4.plist"));
        assertTrue(((NSString)d.objectForKey("Device Name")).toString().equals("Kid\u2019s iPhone"));
    }
    
    public static void testIssue7() throws Exception {
        // also a test for issue 12
        // the issue4 test has a UTF-16-BE string in its binary representation
        NSObject x = PropertyListParser.parse(new File("test/issue4.plist"));
        PropertyListParser.saveAsBinary(x, new File("test/temp/testIssue7.plist"));
        NSObject y = PropertyListParser.parse(new File("test/temp/testIssue7.plist"));
        assertTrue(x.equals(y));
    }
    
    public static void testIssue16() throws Exception {
        float x = ((NSNumber)PropertyListParser.parse(new File("test/issue16.plist"))).floatValue();
        assertTrue(x == (float)2.71828);
    }
    
    public static void testIssue18() throws Exception {
        NSNumber x = new NSNumber(-999);
        PropertyListParser.saveAsBinary(x, new File("test/temp/testIssue18.plist"));
        NSObject y = PropertyListParser.parse(new File("test/temp/testIssue18.plist"));
        assertTrue(x.equals(y));
    }
    
    public static void testIssue21() throws Exception {
        String x = ((NSString)PropertyListParser.parse(new File("test/issue21.plist"))).toString();
        assertTrue(x.equals("Lot&s of &persand&s and other escapable \"\'<>€ characters"));
    }
    
    public static void testIssue22() throws Exception {
        NSDictionary x1 = ((NSDictionary)PropertyListParser.parse(new File("test/issue22-emoji.plist")));
        NSDictionary x2 = ((NSDictionary)PropertyListParser.parse(new File("test/issue22-emoji-xml.plist")));        
        PropertyListParser.saveAsBinary(x1, new File("test/temp/testIssue22.plist"));
        NSDictionary y1 = ((NSDictionary)PropertyListParser.parse(new File("test/temp/testIssue22.plist")));
        PropertyListParser.saveAsXML(x2, new File("test/temp/testIssue22-xml.plist"));
        NSDictionary y2 = ((NSDictionary)PropertyListParser.parse(new File("test/temp/testIssue22-xml.plist")));
        assertTrue(x1.equals(x2));
        assertTrue(x1.equals(y1));
        assertTrue(x1.equals(y2));
        assertTrue(x2.equals(y1));
        assertTrue(x2.equals(y2));
        
        String emojiString = "Test Test, \uD83D\uDE30\u2754\uD83D\uDC4D\uD83D\uDC4E\uD83D\uDD25";        
        
        assertTrue(emojiString.equals(x1.objectForKey("emojiString").toString()));
        assertTrue(emojiString.equals(x2.objectForKey("emojiString").toString()));
        assertTrue(emojiString.equals(y1.objectForKey("emojiString").toString()));
        assertTrue(emojiString.equals(y2.objectForKey("emojiString").toString()));
    }
}
