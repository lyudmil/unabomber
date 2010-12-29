class Location < ActiveRecord::Base
  belongs_to :player
	belongs_to :bomb
end
