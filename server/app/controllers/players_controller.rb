class PlayersController < ApplicationController
  
  def show
    @player = Player.find(params[:id])
    render :text => @player.to_json
  end
  
  def create
    @player = player_with_specified_device_id
    unless @player
      @player = Player.new(:device_id => params[:device_id])
      @player.save
    end
    
    redirect_to @player
  end
  
  def update
    @player = player_with_specified_device_id
    @player.location.destroy if @player.location
    
    @player.location = location_from_params
    @player.save
    
    redirect_to @player
  end
  
  private
  
  def player_with_specified_device_id
    Player.find_by_device_id(params[:device_id])
  end
  
  def location_from_params
    Location.create(:longitude => params[:longitude], :latitude => params[:latitude], :altitude => params[:altitude])
  end

end
