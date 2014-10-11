package lin.core.services;

import java.util.Collection;

import lin.core.entity.Task;
import lin.core.entity.TaskHistory;
import lin.core.entity.TaskStatus;

public interface TaskQueueService {
	/**
	 * 新增任务
	 * 
	 * @return
	 */
	Task addTask(Task task);
	
	/***
	 * 修改任务信息
	 * @param task
	 * @return
	 */
	Task updateTask(Task task);
	
	/**
	 * 添加已完成的任务
	 * @param taskHistory
	 * @return
	 */
	TaskHistory addTaskHistory(TaskHistory taskHistory);
	
	/**
	 * 删除任务记录
	 * @param task
	 */
	void deleteTask(Task task);
	
	/**
	 * 根据hashCode获得Task对象
	 * @param hashCode
	 * @return
	 */
	Task getTaskByHashCode(long hashCode);
	
	/***
	 * 根据id获得task对象
	 * @param id
	 * @return
	 */
	Task getTaskById(long id);
	
	/***
	 * 根据类型获得task对象
	 * @param taskTpye
	 * @return
	 */
	Collection<Task> getTaskByTaskTpye(String taskTpye);
	
	/**
	 * 根据owner获得task对象
	 * @param taskTpye
	 * @return
	 */
	Collection<Task> getTaskByOwner(String owner);

	/**
	 * 根据主键查询任务状态
	 * 
	 * @param id
	 * @return
	 */
	TaskStatus getTaskStatus(Long id);

	/**
	 * 根据拥有者查询其所有任务状态
	 * 
	 * @param owner
	 * @return
	 */
	Collection<TaskStatus> getTaskStatusesByOwner(String owner);

	/**
	 * 根据任务类型查询任务状态
	 * 
	 * @param taskTyps
	 * @return
	 */
	Collection<TaskStatus> getTaskStatusesByType(String taskTyps);

	/**
	 * 根据主键取消任务
	 * 
	 * @param id
	 */
	void cancelTask(Long id);

	/**
	 * 根据任务类型取消任务
	 * 
	 * @param taskType
	 */
	void cancelTasksByType(String taskType);

	/**
	 * 根据拥有者取消任务
	 * 
	 * @param owner
	 */
	void cancelTasksByOwner(String owner);

	/**
	 * 根据主键重启任务
	 * 
	 * @param id
	 */
	void reStartTask(Long id);

	/**
	 * 根据任务类型重启任务
	 * 
	 * @param taskType
	 */
	void reStartTasksByType(String taskType);

	/**
	 * 根据拥有者重启任务
	 * 
	 * @param owner
	 */
	void reStartTasksByOwner(String owner);
}
