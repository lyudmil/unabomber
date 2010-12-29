class Player < ActiveRecord::Base
  has_one :location
	has_many :bombs
end
