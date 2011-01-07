class ApplicationController < ActionController::Base
	
	private

  def player_with_specified_device_id
    Player.find_by_device_id(params[:device_id])
  end


	def location_from_parameters
		Location.create(:longitude => params[:longitude], :latitude => params[:latitude], :altitude => params[:altitude])
	end

end
