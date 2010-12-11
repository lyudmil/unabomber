class PlayersController < ApplicationController
  
  def show
    @player = Player.find(params[:id])
  end
  
  def create
    @player = Player.find_by_device_id(params[:device_id])
    unless @player
      @player = Player.new(:device_id => params[:device_id])
      @player.save
    end
    
    redirect_to @player
  end
  
  def update
  end

end
