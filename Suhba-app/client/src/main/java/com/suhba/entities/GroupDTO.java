public class GroupDTO {
    private long groupId;
    private String groupName;
    private String category; // "Friends", "Family", etc.
    private List<UserDTO> members; // Group participants

    // Getters and Setters
    public long getGroupId() { return groupId; }
    public void setGroupId(long groupId) { this.groupId = groupId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<UserDTO> getMembers() { return members; }
    public void setMembers(List<UserDTO> members) { this.members = members; }
}