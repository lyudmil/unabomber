class LocationsController < ApplicationController
  
  def index
    @locations = Location.all
    render :text => @locations.to_json
  end
  
end
