/* Zhenbin Lin, 114866923, Recitation section 04 */

/**
 * This class represents a block of code. It can either represent a function, loop, statement, etc. Attributes include: name (the block),
 * blockComplexity (the complexity of the current block), highestSubComplexity (highest complexity of block contained within the current block),
 * loopVariable, and blockCount (the number of sub blocks contained within this block)
 * @author zhenb
 *
 */
public class CodeBlock {
	public static final String[] BLOCK_TYPES = {"def", "for", "while", "if", "elif", "else"};
	public static final int DEF = 0, FOR = 1, WHILE = 2, IF = 3, ELIF = 4, ELSE = 5;
	
	public String name;
	public Complexity blockComplexity;
	public Complexity highestSubComplexity;
	public String loopVariable;
	public int blockCount;
	
	/**
	 * Constructor for the CodeBlock object
	 * @param name The block/order in which the code is contained within the function
	 * @param blockComplexity The complexity of the current block
	 * @param highestSubComplexity The highest complexity of the block contained within the current block
	 * @param loopVariable The variable that is used to iterate through a loop
	 */
	public CodeBlock(String name, Complexity blockComplexity, Complexity highestSubComplexity, String loopVariable) {
		this.name = name;
		this.blockComplexity = blockComplexity;
		this.highestSubComplexity = highestSubComplexity;
		this.loopVariable = loopVariable;
		this.blockCount = 0;
	}
	
	/**
	 * A default constructor for the CodeBlock object
	 */
	public CodeBlock() {
		name = null;
		blockComplexity = new Complexity(0,0);
		highestSubComplexity = new Complexity(0,0);
		loopVariable = null;
		blockCount = 0;
	}
	
	/**
	 * Gives the block/order in which the block of code is contained within
	 * @return The block/order in which the block of code is contained within
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gives the complexity of the current block
	 * @return The complexity of the current block
	 */
	public Complexity getBlockComplexity() {
		return blockComplexity;
	}
	
	/**
	 * Gives the highest complexity of the blocks contained within the current block
	 * @return The highest complexity of the blocks within the current block
	 */
	public Complexity getHighestSubComplexity() {
		return highestSubComplexity;
	}
	
	/**
	 * Gives the variable that is used to iterate through a loop
	 * @return The variable used to iterate through the block of code
	 */
	public String getLoopVariable() {
		return loopVariable;
	}
	
	/**
	 * The number of blocks of code within the current block of code
	 * @return
	 */
	public int getBlockCount() {
		return blockCount;
	}
	
	/**
	 * Sets the block/order of the current block of code
	 * @param name The block/order to set the current block to
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the complexity of the current block of code
	 * @param blockComp The complexity to be set to
	 */
	public void setBlockComplexity(Complexity blockComp) {
		blockComplexity.setNPower(blockComp.getNPower());
		blockComplexity.setLogPower(blockComp.getLogPower());
	}
	
	/**
	 * Sets the highest complexity of the blocks contained within
	 * @param highestSubComp The complexity to be set to
	 */
	public void setHighestSubComplexity(Complexity highestSubComp) {
		highestSubComplexity.setNPower(highestSubComp.getNPower());
		highestSubComplexity.setLogPower(highestSubComp.getLogPower());
	}
	
	/**
	 * Sets the variable that is used to iterate through the loop
	 * @param loopVar The variable to be set to
	 */
	public void setLoopVariable(String loopVar) {
		loopVariable = loopVar;
	}
	
	/**
	 * Sets the count of number of blocks contained within the current block
	 * @param count The number to be set as the blockCount
	 */
	public void setBlockCount(int count) {
		blockCount = count;
	}
}