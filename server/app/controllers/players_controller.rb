class PlayersController < ApplicationController
  
  def create
    @player = Player.new(params[:player])
    @player.save
  end
  
  def update
  end
  
end
