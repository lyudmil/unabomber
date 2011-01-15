class BombsController < ApplicationController
	
	def place
		bomb = Bomb.new(:player => player_with_specified_device_id, :location => location_from_parameters)
		bomb.save
	end

end
