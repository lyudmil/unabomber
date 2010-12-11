require 'test_helper'

class LocationsControllerTest < ActionController::TestCase

  test "lists the locations of all players" do
    location_list = flexmock(:to_json => nil)
    flexmock(Location).should_receive(:all).once.and_return(location_list)
    
    get :index
    
    assert_equal location_list, assigns(:locations)
  end
  
end
