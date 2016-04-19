import java.util.ArrayList;

/**
 * Heather Bradfield
 * 12-3-15
 */
public class Profile<T> implements Comparable<T>
{
    private String name;
    private String relationshipStatus;
    private String residence;
    private String phone;
    private ArrayList<Profile> friends;

    public Profile( String name, String status, String residence, String phone)
    {
        this.name = name.toUpperCase();
        this.relationshipStatus = status;
        this.residence = residence;
        this.phone = phone;
        this.friends = new ArrayList<>();
    }

    public void setName(String newName)
    {
        this.name = newName;
    }

    public String getName()
    {
        return this.name;
    }

    public void setStatus(String newStatus)
    {
        this.relationshipStatus = newStatus;
    }

    public String getStatus()
    {
        return this.relationshipStatus;
    }

    public void setResidence(String newResidence)
    {
        this.residence = newResidence;
    }

    public String getResidence()
    {
        return this.residence;
    }

    public void setPhone(String newPhone)
    {
        this.phone = newPhone;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public ArrayList<Profile> getFriends()
    {
        return this.friends;
    }

    public void addFriend(Profile newFriend)
    {
        if (!this.friends.contains(newFriend)) this.friends.add(newFriend);
        //ArrayList<Profile> newFriendsList = newFriend.getFriends();
        //newFriendsList.add(this profile)
    }
    public int compareTo(Object other)
    {
        Profile otherProfile = (Profile)other;
        int result = this.name.compareTo(otherProfile.getName());
        return result;
    }

    public String toString()
    {
        return this.name + "";
    }
}
