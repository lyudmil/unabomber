class PlayersController < ApplicationController
  
  def show
    @player = Player.find(params[:id])
    render :text => @player.to_json
  end
  
  def create
    @player = player_with_specified_device_id
    unless @player
      @player = Player.new(:device_id => params[:device_id])
      @player.assign_role
      @player.save
    end
    
    redirect_to @player
  end
  
  def update
    @player = player_with_specified_device_id
    @player.location.destroy if @player.location
    
    @player.location = location_from_parameters
    @player.save
    
    redirect_to @player
  end
  
  def arrest
    @player = player_with_specified_device_id
    suspect = Player.find(params[:id])
    suspect.arrested = true
    suspect.save
  end
  
  def index
    @active_players = Player.all.select { |player| player.active? }
    @locations = @active_players.collect { |player| player.location }
    @locations.reject! { |location| location == nil }
    render :text => @locations.to_json
  end
  
end
