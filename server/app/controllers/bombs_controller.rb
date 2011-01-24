class BombsController < ApplicationController
	
	def place
		@bomb = Bomb.new(:player => player_with_specified_device_id, :location => location_from_parameters)
		@bomb.save
		
		redirect_to @bomb
	end
	
	def show
	  @bomb = Bomb.find(params[:id])
	  render :text => @bomb.to_json
  end
	
	def detonate
	  bomb = Bomb.find(params[:id])
	  Player.all.each do |player|
	      player.killed = true if player.within_range_of(bomb.location)
    end
  end

end
