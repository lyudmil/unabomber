class StatusController < ApplicationController
  
  def index
    @player = player_with_specified_device_id
    return unless @player
    
    @citizens = Player.where(:role => :citizen).select { |citizen| citizen.active? }
    
    if @player.killed?
      render :text => 'finished-killed'
      return
    end
    
    if @player.arrested?
      render :text => 'finished-jail'
      return
    end
    
    if @player.role != :unabomber
      unabombers = Player.where(:role => :unabomber).select { |unabomber| unabomber.active? }
      if unabombers.empty?
        render :text => 'finished-win' 
        return
      end
      
      if @citizens.empty?
        render :text => 'finished-lose'
        return
      end
    end  
    
    if @player.role == :unabomber
      if @citizens.empty?
        render :text => 'finished-win'
        return
      end
    end
    
    render :text => 'started'
  end
  
end
