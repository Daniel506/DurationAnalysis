package mk.ukim.finki.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "consonant", catalog = "duration_db")
public class Consonant {

		private String id;
		private Boolean cvox;
		private String cplace;
		private String ctype;
		
		@Id
		@Column(name = "ID", unique = true, nullable = false)
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		@Column(name = "CVOX")
		public Boolean getCvox() {
			return cvox;
		}
		
		public void setCvox(Boolean cvox) {
			this.cvox = cvox;
		}
		
		@Column(name = "CPLACE")
		public String getCplace() {
			return cplace;
		}
		
		public void setCplace(String cplace) {
			this.cplace = cplace;
		}
		
		@Column(name = "CTYPE")
		public String getCtype() {
			return ctype;
		}
		
		public void setCtype(String ctype) {
			this.ctype = ctype;
		}
}
