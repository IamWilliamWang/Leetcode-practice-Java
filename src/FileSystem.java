import java.util.*;

class File {
    private String name;
    private String content;

    public File(String name) {
        this.name = name;
    }

    public File(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void appendContent(String content) {
        this.content += content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof File)
            return this.name.equals(((File) obj).name);
        return super.equals(obj);
    }
}

class Folder extends File {
    private LinkedList<Folder> folders = new LinkedList<>();
    private LinkedList<File> files = new LinkedList<>();

    public Folder(String name) {
        super(name);
    }

    public Folder getFolderAt(int index) {
        return this.folders.get(index);
    }

    public LinkedList<Folder> getFolders() {
        return folders;
    }

    public File getFileAt(int index) {
        return this.files.get(index);
    }

    public LinkedList<File> getFiles() {
        return files;
    }
}

class FileSystem {
    private Folder rootFolder = new Folder("");

    public FileSystem() {
    }

    private Folder getNowFolder(String path) {
        Folder nowFolder = this.rootFolder;
        for (String name : path.split("/")) {
            if (name.isEmpty())
                continue;
            int index = nowFolder.getFolders().indexOf(new Folder(name));
            if (index != -1)
                nowFolder = nowFolder.getFolderAt(index);
            else
                return nowFolder;
        }
        return nowFolder;
    }

    public List<String> ls(String path) {
        Folder nowFolder = this.rootFolder;
        List<File> lsFiles = new LinkedList<>();
        for (String name : path.split("/")) {
            if (name.isEmpty())
                continue;
            int index = nowFolder.getFolders().indexOf(new Folder(name));
            if (index != -1) {
                nowFolder = nowFolder.getFolderAt(index);
            } else {
                index = nowFolder.getFiles().indexOf(new File(name));
                LinkedList<String> ret = new LinkedList<>();
                if (index != -1)
                    ret.add(name);
                return ret;
            }
        }
        lsFiles.addAll(nowFolder.getFolders());
        lsFiles.addAll(nowFolder.getFiles());
        lsFiles.sort(Comparator.comparing(File::getName));
        ArrayList<String> result = new ArrayList<>();
        for (File file : lsFiles)
            result.add(file.getName());
        return result;
    }

    public void mkdir(String path) {
        Folder nowFolder = this.rootFolder;
        String[] splitPath = path.split("/");
        int pathI;
        for (pathI = 0; pathI < splitPath.length; pathI++) {
            String name = splitPath[pathI];
            if (name.isEmpty())
                continue;
            int index = nowFolder.getFolders().indexOf(new Folder(name));
            if (index == -1)
                break;
            nowFolder = nowFolder.getFolderAt(index);
        }
        for (; pathI < splitPath.length; pathI++) {
            String name = splitPath[pathI];
            Folder newFolder = new Folder(name);
            nowFolder.getFolders().add(newFolder);
            nowFolder = newFolder;
        }
    }

    public void addContentToFile(String filePath, String content) {
        Folder nowFolder = getNowFolder(filePath);
        String[] ret = filePath.split("/");
        String filename = ret[ret.length - 1];
        int fileIndex = nowFolder.getFiles().indexOf(new File(filename));
        if (fileIndex == -1) {
            File newFile = new File(filename, content);
            nowFolder.getFiles().add(newFile);
        } else {
            nowFolder.getFileAt(fileIndex).appendContent(content);
        }
    }

    public String readContentFromFile(String filePath) {
        Folder nowFolder = getNowFolder(filePath);
        String[] ret = filePath.split("/");
        String filename = ret[ret.length - 1];
        int fileIndex = nowFolder.getFiles().indexOf(new File(filename));
        if (fileIndex == -1)
            return "";
        return nowFolder.getFileAt(fileIndex).getContent();
    }

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        while (true) {
            String[] input = new Scanner(System.in).nextLine().split(" ");
            switch (input[0]) {
                case "ls":
                    System.out.println(fs.ls(input[1]));
                    break;
                case "mkdir":
                    fs.mkdir(input[1]);
                    System.out.println("Done");
                    break;
                case "add":
                    fs.addContentToFile(input[1], input[2]);
                    System.out.println("Done");
                    break;
                case "read":
                    System.out.println(fs.readContentFromFile(input[1]));
                    break;
            }
        }
    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */