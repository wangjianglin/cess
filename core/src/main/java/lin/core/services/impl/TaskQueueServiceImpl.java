package lin.core.services.impl;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lin.core.entity.Task;
import lin.core.entity.TaskHistory;
import lin.core.entity.TaskStatus;
import lin.core.jpa.CommonQuery;
import lin.core.jpa.CommonQueryParams;
import lin.core.services.TaskQueueService;

@Component
@Transactional
public class TaskQueueServiceImpl implements TaskQueueService {
	private EntityManager jpa;

	//@PersistenceContext
	public void setJpa(EntityManager jpa) {
		this.jpa = jpa;
	}

	@Override
	public Task addTask(Task task) {
		return CommonQuery.update(jpa, task);
	}

	@Override
	public Task updateTask(Task task) {
		Task task1 = jpa.find(Task.class, task.getId());
		task1.setAddDate(task.getAddDate());
		task1.setExecutionDate(task.getExecutionDate());
		task1.setHashCode(task.getHashCode());
		task1.setNumberOfTimes(task.getNumberOfTimes());
		task1.setOwner(task.getOwner());
		task1.setStatus(task.getStatus());
		task1.setTaskObject(task.getTaskObject());
		task1.setType(task.getType());
		task1 = CommonQuery.update(jpa, task1);
		return task1;
	}

	@Override
	public TaskStatus getTaskStatus(Long id) {
		Task task = jpa.find(Task.class, id);
		TaskStatus taskStatus = task.getStatus();
		return taskStatus;
	}

	@Override
	public Collection<TaskStatus> getTaskStatusesByOwner(String owner) {
		return CommonQuery.nameQuery(jpa, new CommonQueryParams<TaskStatus>("findTaskStausByOwner",
				owner));
	}

	@Override
	public Collection<TaskStatus> getTaskStatusesByType(String taskTyps) {
		return CommonQuery.nameQuery(jpa, new CommonQueryParams<TaskStatus>("findTaskStausByType",
				taskTyps));
	}

	@Override
	public void cancelTask(Long id) {
		//Task task = jpa.find(Task.class, id);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Task getTaskByHashCode(long hashCode) {
		Query query = jpa.createNamedQuery("findTaskByHashCode");
		query.setParameter(1, hashCode);
		List list =  query.getResultList();
		Task task = (Task)list.get(0);
		return task;
	}

	@Override
	public void cancelTasksByType(String taskType) {

	}

	@Override
	public void cancelTasksByOwner(String owner) {

	}

	@Override
	public void reStartTask(Long id) {

	}

	@Override
	public void reStartTasksByType(String taskType) {

	}

	@Override
	public void reStartTasksByOwner(String owner) {

	}

	@Override
	public TaskHistory addTaskHistory(TaskHistory taskHistory) {
		TaskHistory t = CommonQuery.update(jpa, taskHistory);
		return t;
	}

	@Override
	public void deleteTask(Task task) {
		Task t = jpa.find(Task.class, task.getId());
		jpa.remove(t);
	}

	@Override
	public Task getTaskById(long id) {
		Task t = jpa.find(Task.class, id);
		return t;
	}

	@Override
	public Collection<Task> getTaskByTaskTpye(String taskTpye) {
		Collection<Task> tasks = CommonQuery.nameQuery(jpa, new CommonQueryParams<Task>("findTaskByType", taskTpye));
		return tasks;
	}

	@Override
	public Collection<Task> getTaskByOwner(String owner) {
		Collection<Task> tasks = CommonQuery.nameQuery(jpa, new CommonQueryParams<Task>("findTaskByOwner", owner));
		return tasks;
	}
}
