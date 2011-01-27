class StatusController < ApplicationController
  
  def index
    @status = 'started'
    return unless @player = player_with_specified_device_id
    
    @citizens = Player.where(:role => 'citizen').select { |citizen| citizen.active? }
    
    if Player.count < 3
      render :text => @status and return
    end
    
    if @player.killed?
      render :text => 'finished-killed' and return
    end
    
    if @player.arrested?
      render :text => 'finished-jail' and return
    end
    
    figure_out_status_based_on_role
    
    render :text => @status
  end
  
  private
  
  def figure_out_status_based_on_role
    figure_out_status_if_good_guy
    figure_out_status_if_bad_guy
  end
  
  def figure_out_status_if_good_guy
    if @player.role != 'unabomber'
      @status = 'finished-lose' if @citizens.empty?

      @unabombers = Player.where(:role => 'unabomber').select { |unabomber| unabomber.active? }
      @status = 'finished-win' if @unabombers.empty?
    end  
  end
  
  def figure_out_status_if_bad_guy
    if @player.role == 'unabomber'
      @status = 'finished-win' if @citizens.empty?
    end
  end
end
