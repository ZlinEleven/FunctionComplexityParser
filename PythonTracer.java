import java.io.*;
import java.util.Scanner;

/**
 * This class contains the main method which a menu driven application.
 * The program prompts the user for a file name that contains a function
 * to dissect the time complexity of the function. The user can also choose to
 * 'quit' to terminate the program
 * @author zhenb
 *
 */
public class PythonTracer {

	static final int SPACE_COUNT = 4;
	
	public static void main(String[] args) throws Exception{
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Please enter a file name (or 'quit' to quit): ");
		String fileName = scan.nextLine();
		System.out.println();
		
		
		while(!fileName.equals("quit")){
			try {
				Complexity finalComplexity = traceFile(fileName);
				System.out.println("Overall complexity of " + fileName.split("\\.")[0] + ": " + finalComplexity.toString());
				
			}
			catch(Exception e) {
				System.out.println("Invalid file name.");
			}
			
			System.out.println();
			System.out.print("Please enter a file name (or 'quit' to quit): ");
			fileName = scan.nextLine();
            System.out.println();
		}
		
		System.out.print("Program terminating successfully...");

	}
	
	public static Complexity traceFile(String fileName) throws Exception {
		if(fileName == null) {
			throw new FileNotFoundException();
		}
		
		BlockStack stack = new BlockStack();
		
		Scanner reader = new Scanner(new File(fileName));
		
		String currentLine;

		while(reader.hasNextLine()) {
			currentLine = reader.nextLine();
			if(!currentLine.isBlank() && !currentLine.contains("#")) {			
				int spaceCount = 0;
				while(currentLine.charAt(0) == ' ') {
					spaceCount++;
					currentLine = currentLine.substring(1);
				}
				int indents = spaceCount/SPACE_COUNT;
				while(indents < stack.size()) {
					if(indents == 0) {
						System.out.println("    Leaving block " + stack.peek().getName() + ".");
						CodeBlock finalBlock = stack.pop();
						int totalNPower = finalBlock.getBlockComplexity().getNPower() + finalBlock.getHighestSubComplexity().getNPower();
						int totalLogPower = finalBlock.getBlockComplexity().getLogPower() + finalBlock.getHighestSubComplexity().getLogPower();
						return new Complexity(totalNPower, totalLogPower);
					}
					else {
						CodeBlock oldTop = stack.pop();
						Complexity oldTopComplexity = new Complexity(oldTop.getBlockComplexity().getNPower() + oldTop.getHighestSubComplexity().getNPower(), oldTop.getBlockComplexity().getLogPower() + oldTop.getHighestSubComplexity().getLogPower());
						if(oldTopComplexity.getNPower() >= stack.peek().getHighestSubComplexity().getNPower()) {
							if(oldTopComplexity.getNPower() > stack.peek().getHighestSubComplexity().getNPower()) {
								stack.peek().setHighestSubComplexity(oldTopComplexity);
								System.out.println("    Leaving block " + oldTop.getName() + ", updating block " + stack.peek().getName() + ":");
							}
							else {
								if(oldTopComplexity.getLogPower() > stack.peek().getHighestSubComplexity().getLogPower()) {
									stack.peek().setHighestSubComplexity(oldTopComplexity);
									System.out.println("    Leaving block " + oldTop.getName() + ", updating block " + stack.peek().getName() + ":");
								}
								else{
									System.out.println("    Leaving block " + oldTop.getName() + ", nothing to update.");
								}
							}
						}
						else{
							System.out.println("    Leaving block " + oldTop.getName() + ", nothing to update.");
						}
						System.out.format("%-23s%-30s%-30s", "        Block " + stack.peek().getName() + ":", "block complexity = " + stack.peek().getBlockComplexity().toString(), "highest sub-complexity = " + stack.peek().getHighestSubComplexity().toString());
						System.out.println("\n");
					}
				}
				if(!checkKeyword(currentLine).equals("")) {
					String keyword = checkKeyword(currentLine);
					
					CodeBlock newPush = new CodeBlock();
					if(stack.isEmpty()) {
						newPush.setName("1");
					}
					else {
						stack.peek().setBlockCount(stack.peek().getBlockCount() + 1);
						newPush.setName(stack.peek().getName() + "." + (stack.peek().getBlockCount()));
					}
					
					
					if(keyword.equals("for")) {
						if(currentLine.contains(" N:")) {
							newPush.setBlockComplexity(new Complexity(1, 0));
							stack.push(newPush);
						}
						else if(currentLine.contains("log_N:")) {
							newPush.setBlockComplexity(new Complexity(0, 1));
							stack.push(newPush);
						}
					}
					else if(keyword.equals("while")) {
						newPush.setLoopVariable(currentLine.trim().split(" ")[1]);
						newPush.setBlockComplexity(new Complexity(0, 0));
						stack.push(newPush);
					}
					else {
						newPush.setBlockComplexity(new Complexity(0, 0));
						stack.push(newPush);
					}
					System.out.println("    Entering block " + newPush.getName() + " '" + keyword + "':");
					System.out.format("%-23s%-30s%-30s", "        Block " + newPush.getName() + ":", "block complexity = " + newPush.getBlockComplexity().toString(), "highest sub-complexity = " + newPush.getHighestSubComplexity().toString());
					System.out.println("\n");
				}
				else if(stack.size() > 0 && stack.peek().getLoopVariable() != null && currentLine.contains(stack.peek().getLoopVariable()) && (currentLine.contains("-=") || currentLine.contains("/="))) {
					if(currentLine.contains("-=")) {
						stack.peek().getBlockComplexity().setNPower(1);
					}
					else {
						stack.peek().getBlockComplexity().setLogPower(1);
					}
                    System.out.println("    Found update statement, updating block " + stack.peek().getName() + ":");
                    System.out.format("%-23s%-30s%-30s", "        Block " + stack.peek().getName() + ":", "block complexity = " + stack.peek().getBlockComplexity().toString(), "highest sub-complexity = " + stack.peek().getHighestSubComplexity().toString());
                    System.out.println("\n");
				}
			}
		}
			
	
		while(stack.size() > 1) {
			CodeBlock oldTop = stack.pop();
			Complexity oldTopComplexity = new Complexity(oldTop.getBlockComplexity().getNPower() + oldTop.getHighestSubComplexity().getNPower(), oldTop.getBlockComplexity().getLogPower() + oldTop.getHighestSubComplexity().getLogPower());
			if(oldTopComplexity.getNPower() >= stack.peek().getHighestSubComplexity().getNPower()) {
				if(oldTopComplexity.getNPower() > stack.peek().getHighestSubComplexity().getNPower()) {
					stack.peek().setHighestSubComplexity(oldTopComplexity);
					System.out.println("    Leaving block " + oldTop.getName() + ", updating block " + stack.peek().getName() + ":");
				}
				else {
					if(oldTopComplexity.getLogPower() > stack.peek().getHighestSubComplexity().getLogPower()) {
						stack.peek().setHighestSubComplexity(oldTopComplexity);
						System.out.println("    Leaving block " + oldTop.getName() + ", updating block " + stack.peek().getName() + ":");
					}
				}
			}
			else{
				System.out.println("    Leaving block " + oldTop.getName() + ", nothing to update.");
			}
			System.out.format("%-23s%-30s%-30s", "        Block " + stack.peek().getName() + ":", "block complexity = " + stack.peek().getBlockComplexity().toString(), "highest sub-complexity = " + stack.peek().getHighestSubComplexity().toString());
			System.out.println("\n");
		}
		System.out.println("    Leaving block " + stack.peek().getName() + ".\n");
		CodeBlock finalBlock = stack.pop();
		int totalNPower = finalBlock.getBlockComplexity().getNPower() + finalBlock.getHighestSubComplexity().getNPower();
		int totalLogPower = finalBlock.getBlockComplexity().getLogPower() + finalBlock.getHighestSubComplexity().getLogPower();
		return new Complexity(totalNPower, totalLogPower);
	}

	public static String checkKeyword(String currentLine){
		int n = currentLine.length();
		if(n > 4 && currentLine.substring(0, 4).equals("def ")){
			return "def";
		}
		else if(n >= 4 && currentLine.substring(0, 4).equals("for ")){
			return "for";
		}
		else if(n >= 6 && currentLine.substring(0, 6).equals("while ")){
			return "while";
		}
		else if(n >= 3 && currentLine.substring(0, 3).equals("if ")){
			return "if";
		}
		else if(n >= 5 && currentLine.substring(0, 5).equals("elif ")){
			return "elif";
		}
		else if(n >= 5 && currentLine.substring(0,5).equals("else:")){
			return "else";
		}
		else{
			return "";
		}
	}
}
