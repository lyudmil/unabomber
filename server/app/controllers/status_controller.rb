class StatusController < ApplicationController
  
  def index
    player = Player.find_by_device_id(params[:device_id])
    citizens = Player.where(:role => :citizen)
    
    unless player
      render :text => 'finished-killed'
      return
    end
    
    if player.role != :unabomber
      unabombers = Player.where(:role => :unabomber)
      if unabombers.empty?
        render :text => 'finished-win' 
        return
      end
      
      if citizens.empty?
        render :text => 'finished-lose'
        return
      end
    end  
    
    if player.role == :unabomber
      if citizens.empty?
        render :text => 'finished-win'
        return
      end
    end
    
    render :text => 'started'
  end
  
end
