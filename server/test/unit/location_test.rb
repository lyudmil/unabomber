require 'test_helper'

class LocationTest < ActiveSupport::TestCase
  
  test "has longitude latitude and altitude" do
    location = Location.new(:longitude => 9.0005, :latitude => 39.0001001, :altitude => 400.4)
    
    assert location.save
  end
  
end
