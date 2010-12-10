class PlayersController < ApplicationController
  
  def create
    return if existing_player
    @player = Player.new(:device_id => params[:device_id])
    @player.save
  end
  
  def update
  end
  
  private
  
  def existing_player
    Player.find_by_device_id(params[:device_id])
  end
end
