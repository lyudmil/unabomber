class Player < ActiveRecord::Base
  has_one :location
	has_many :bombs
	
	def assign_role
	  return if self.role
	  
	  player_group = Player.count % 10
	    
	  if player_group == 0
	    self.role = :policeman
    end
    
    if player_group == 1
      self.role = :unabomber
    end
    
    if player_group > 1
      self.role = :citizen
    end
  end
end
