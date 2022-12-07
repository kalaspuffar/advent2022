public class FileEntry {
    private final String name;
    private final long size;

    public FileEntry(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long size() {
        return size;
    }
}
