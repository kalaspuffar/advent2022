import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day7 {
    public static void main(String[] args) throws Exception{
        String input = """
$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
""";

        BufferedReader br = new BufferedReader(new FileReader("inputs/day7.txt"));

        DirectoryEntry rootDirectory = new DirectoryEntry(null,"/");
        DirectoryEntry currentDirectory = rootDirectory;

        List<DirectoryEntry> allDirectories = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            String[] linearr = line.split(" ");
            if ("$".equals(linearr[0])) {
                if ("cd".equals(linearr[1])) {
                    if ("/".equals(linearr[2])) {
                        currentDirectory = rootDirectory;
                    } else if ("..".equals(linearr[2])) {
                        currentDirectory = currentDirectory.getParent();
                    } else {
                        currentDirectory = currentDirectory.getDir(linearr[2]);
                    }
                }
            } else if ("dir".equals(linearr[0])) {
                DirectoryEntry de = new DirectoryEntry(currentDirectory, linearr[1]);
                currentDirectory.addFile(de);
                allDirectories.add(de);
            } else {
                currentDirectory.addFile(new FileEntry(linearr[1], Long.parseLong(linearr[0])));
            }
        }

        long sizeOfRoot = rootDirectory.size();
        long spaceLeft = 70000000 - sizeOfRoot;
        long spaceToRemove = 30000000 - spaceLeft;

        List<DirectoryEntry> candidates = new ArrayList<>();

        long count = 0;
        for (DirectoryEntry de : allDirectories) {
            if (de.size() > spaceToRemove) {
                candidates.add(de);
            }
        }

        candidates.add(rootDirectory);

        candidates.sort((a, b) -> (int)(a.size() - b.size()));

        System.out.println(candidates.get(0).size());
    }
}
