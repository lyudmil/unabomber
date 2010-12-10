class PlayersController < ApplicationController
  
  def create
    @player = Player.new(:device_id => params[:device_id])
    @player.save
  end
  
  def update
  end
  
end
