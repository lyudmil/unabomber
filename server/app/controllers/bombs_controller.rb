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
	    if player.within_range_of(bomb.location)
	      player.location.destroy
	      player.destroy
      end
    end
  end

end
