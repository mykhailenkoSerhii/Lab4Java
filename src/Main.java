import java.io.*;
import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input desired task: ");
		int task = scanner.nextInt();
		if(task == 1)
			task1(scanner);
		else if(task == 2)
			task2(scanner);
	}

	public static BufferedReader createFileReader(File file)
	{
		try
		{
			return new BufferedReader(new FileReader(file));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
			return null;
		}
	}

	public static BufferedWriter createFileWriter(File file, boolean append)
	{
		try
		{
			return new BufferedWriter(new FileWriter(file, append));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
			return null;
		}
		catch(IOException e)
		{
			System.out.println("Couldn't write to the file.");
			return null;
		}
	}

	public static void task1(Scanner scanner)
	{
		BufferedReader fileAReader = null;
		BufferedReader fileBReader = null;

		while(fileAReader == null)
		{
			System.out.println("Please input the name of File A");
			String fileName = scanner.next();
			File file = new File(fileName);
			fileAReader = createFileReader(file);
		}
		while(fileBReader == null)
		{
			System.out.println("Please input the name of File B");
			String fileName = scanner.next();
			File file = new File(fileName);
			fileBReader = createFileReader(file);
		}

		List<String> wordsA = new ArrayList<>();
		List<String> wordsB = new ArrayList<>();

		try
		{
			String line;
			while((line = fileAReader.readLine()) != null)
				wordsA.addAll(List.of(line.split("[ ,.:;-?!]+")));
			while((line = fileBReader.readLine()) != null)
				wordsB.addAll(List.of(line.split("[ ,.:;-?!]+")));

			fileAReader.close();
			fileBReader.close();
		}
		catch (IOException e)
		{
			System.out.println("Error reading file!");
			return;
		}

		Set<String> allWords = new HashSet<>();
		allWords.addAll(wordsA);
		allWords.addAll(wordsB);

		List<String> commonWords = new ArrayList<>();
		for(String word : allWords)
		{
			if(wordsB.contains(word) && wordsA.contains(word))
				commonWords.add(word);
		}
		List<String> separateWords = new ArrayList<>();
		for(String word : allWords)
		{
			if(wordsA.contains(word) && !wordsB.contains(word))
				separateWords.add(word);
			else if(wordsB.contains(word) && !wordsA.contains(word))
				separateWords.add(word);
		}
		System.out.println("Finished reading files!");

		BufferedWriter writer = null;
		while(writer == null)
		{
			System.out.println("Where to output separate words? : ");
			String fileName = scanner.next();
			File file = new File(fileName);
			if(!file.exists())
			{
				System.out.println("File not found! Create it? ");
				String check = scanner.next();
				if(check.equalsIgnoreCase("yes"))
					writer = createFileWriter(file, false);
			}
			else
			{
				System.out.println("File already exists! Append to it?");
				String check = scanner.next();
				boolean append = check.equalsIgnoreCase("yes");
				writer = createFileWriter(file, append);
			}
		}

		StringBuilder separatedBuilder = new StringBuilder();
		for(String word : separateWords)
			separatedBuilder.append(word).append("\n");

		try
		{
			writer.write(separatedBuilder.toString());
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println("An error occured writing the result to a file!");
		}
		System.out.println("Finished writing files!");

		System.out.println("Printing out all common words of both files: ");
		for(String word : commonWords)
			System.out.println(word);
	}

	public static void task2(Scanner scanner)
	{
		BufferedReader reader = null;
		while(reader == null)
		{
			System.out.println("Please input the name of input file:");
			String fileName = scanner.next();
			File file = new File(fileName);
			reader = createFileReader(file);
		}

		BufferedWriter writer = null;
		while(writer == null)
		{
			System.out.println("Please input the name of target file:");
			String fileName = scanner.next();
			File file = new File(fileName);
			if(!file.exists())
			{
				System.out.println("File not found! Create it? ");
				String check = scanner.next();
				if(check.equalsIgnoreCase("yes"))
					writer = createFileWriter(file, false);
			}
			else
			{
				System.out.println("File already exists! Append to it?");
				String check = scanner.next();
				boolean append = check.equalsIgnoreCase("yes");
				writer = createFileWriter(file, append);
			}
		}

		try
		{
			String line;
			while((line = reader.readLine()) != null)
			{
				String trimmedLine = line.trim();
				if(trimmedLine.isEmpty())
					continue;
				String[] words = trimmedLine.split("\\s+");
				for(String word : words)
				{
					writer.write(word);
					writer.newLine();
				}
			}

			reader.close();
			writer.close();
			System.out.println("Finished reading and writing files!");
		}
		catch(IOException e)
		{
			System.out.println("An error occurred while reading or writing files!");
		}
	}
}
