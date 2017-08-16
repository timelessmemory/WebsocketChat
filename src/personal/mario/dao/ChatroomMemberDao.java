package personal.mario.dao;

import java.util.List;

public interface ChatroomMemberDao {
	public void save(String uname);
	public void remove(String uname);
	public List<String> getAll();
}
