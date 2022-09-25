/* Zhenbin Lin, 114866923, Recitation section 04 */

/**
 * A basic stack class with a capacity of 20
 * @author zhenb
 *
 */
public class BlockStack {
	public static final int CAPACITY = 20;
	private CodeBlock[] data;
	private int top;
	
	/**
	 * The constructor of the BlockStack (stack) object. Initializes an empty CodeBLock array with capacity 20
	 */
	public BlockStack() {
		data = new CodeBlock[CAPACITY];
		top = -1;
	}
	
	/**
	 * Inserts a new CodeBlock object at the top of the stack, given the stack isn't full
	 * @param block The CodeBlock object to be pushed
	 * @throws Exception If the stack is full, no additional CodeBlock objects can be added
	 */
	public void push(CodeBlock block) throws Exception {
		if(top + 1 == CAPACITY) {
			throw new Exception("Full stack, cannot push additional blocks");
		}
		top++;
		data[top] = block;
	}
	
	/**
	 * Removes the CodeBlock object at the top of the stack, given the stack isn't empty
	 * @return The CodeBlock object that was popped
	 * @throws Exception If the stack is empty, there is no CodeBlock objects to be popped
	 */
	public CodeBlock pop() throws Exception {
		if(top == -1) {
			throw new Exception("Empty stack, no blocks to be popped");
		}
		CodeBlock temp = data[top];
		top--;
		return temp;
	}
	
	/**
	 * Gives the CodeBlock object at the top of the stack
	 * @return The CodeBlock at the top of the stack
	 * @throws Exception If the stack is empty, there is no CodeBlock to look at
	 */
	public CodeBlock peek() throws Exception {
		if(top == -1) {
			throw new Exception("Empty stack, no blocks to peek at");
		}
		return data[top];
	}
	
	public int size() {
		return top + 1;
	}
	
	public boolean isEmpty() {
		return top == -1;
	}
}
