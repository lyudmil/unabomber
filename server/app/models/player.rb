class Player < ActiveRecord::Base
  has_one :location
	has_many :bombs
	
	def within_range_of explosion
	  distance = self.location.distance_to(explosion) * 1000
	  distance <= 20
  end
  
	def assign_role
	  return if self.role
	  
	  case Player.count % 10
	    when 0
	      self.role = :policeman
      when 1
        self.role = :unabomber
      else
        self.role = :citizen
    end
    
  end
end
