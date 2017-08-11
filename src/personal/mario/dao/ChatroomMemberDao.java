package personal.mario.dao;

import java.util.List;

public interface ChatroomMemberDao {
	public void save(String chatroom, String uname);
	public void remove(String chatroom, String uname);
	public List<String> getAll(String chatroom);
}
