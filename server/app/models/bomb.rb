class Bomb < ActiveRecord::Base
	has_one :location
	belongs_to :player
end
