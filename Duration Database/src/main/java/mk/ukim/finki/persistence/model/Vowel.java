package mk.ukim.finki.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vowel", catalog = "duration_db")
public class Vowel {

		private String id;
		private Boolean vround;
		private int vfront;
		private int vheight;
		private String vlng;
		
		@Id
		@Column(name = "ID", unique = true, nullable = false)
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		@Column(name = "VROUND")
		public Boolean getVround() {
			return vround;
		}
		
		public void setVround(Boolean vround) {
			this.vround = vround;
		}
		
		@Column(name = "VFRONT")
		public int getVfront() {
			return vfront;
		}
		
		public void setVfront(int vfront) {
			this.vfront = vfront;
		}
		
		@Column(name = "VHEIGHT")
		public int getVheight() {
			return vheight;
		}
		
		public void setVheight(int vheight) {
			this.vheight = vheight;
		}
		
		@Column(name = "VLNG")
		public String getVlng() {
			return vlng;
		}
		
		public void setVlng(String vlng) {
			this.vlng = vlng;
		}
		
}
