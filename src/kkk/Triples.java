package kkk;


import java.util.Objects;

public class Triples  {

	private String subject,predicate,object;
	private Object object1;
	
	public Triples(String subject,String predicate,Object object) {
		
		this.subject=subject;
		this.predicate=predicate;
		this.object1=object;
		// TODO Auto-generated constructor stub
		
	}
	
	
	public Triples(String subject,String predicate,String object) {
		this.subject=subject;
		this.predicate=predicate;
		this.object=object;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}
	
	
	
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Triples triples = (Triples) o;
	    return Objects.equals(subject, triples.subject) &&
	           Objects.equals(predicate, triples.predicate) &&
	           Objects.equals(object, triples.object);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(subject, predicate, object);
	}
	
	
	
}
