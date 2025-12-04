package in.co.rays.proj4.bean;

/**
 * CourseBean represents the Course entity.
 * <p>
 * It contains basic details of a course like name, duration and description.
 * It also supports dropdown list functionality using key-value mapping.
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public class CourseBean extends BaseBean {

	private String name;
	private String duration;
	private String description;

	/**
	 * Gets the course name.
	 * 
	 * @return course name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the course name.
	 * 
	 * @param name the course name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the course duration.
	 * 
	 * @return course duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the course duration.
	 * 
	 * @param duration the course duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Gets the course description.
	 * 
	 * @return course description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the course description.
	 * 
	 * @param description the course description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns key for dropdown list.
	 * 
	 * @return id as String
	 */
	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	/**
	 * Returns display value for dropdown list.
	 * 
	 * @return course name
	 */
	@Override
	public String getValue() {
		return name;
	}
}
