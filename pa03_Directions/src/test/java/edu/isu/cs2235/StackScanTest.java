package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.Stack;
import edu.isu.cs.cs3308.structures.impl.LinkedStack;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fate
 */
public class StackScanTest {

    public StackScanTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of scanStack method, of class StackScan.
     */
    @Test
    public void testScanStack() {
        Stack<Integer> stack = new LinkedStack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        assertTrue("Scan did not find element known to be present", StackScan.scanStack(stack, 0));
        assertFalse("Scan destorys stack", stack.isEmpty());

        for (int i = 9; i >= 0; i--) {
            assertEquals("Scan does not put stack back together in original order", new Integer(i), stack.pop());
        }
    }

    /**
     * Test of scanStack method, of class StackScan.
     */
    @Test
    public void testScanStack_2() {
        assertFalse("Scan does not return false on null stack", StackScan.scanStack(null, 0));
    }

    /**
     * Test of scanStack method, of class StackScan.
     */
    @Test
    public void testScanStack_3() {
        Stack<Integer> stack = new LinkedStack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        assertFalse("Scan does not return false on null element", StackScan.scanStack(stack, null));
    }

    /**
     * Test of scanStack method, of class StackScan.
     */
    @Test
    public void testScanStack_4() {
        Stack<Integer> stack = new LinkedStack<>();

        assertFalse("Scan does not return false on empty stack", StackScan.scanStack(stack, 0));
    }

    /**
     * Test of scanStack method, of class StackScan.
     */
    @Test
    public void testScanStack_5() {
        Stack<Integer> stack = new LinkedStack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        assertFalse("Scan does not return false element not contained in stack", StackScan.scanStack(stack, 20));
    }
}
