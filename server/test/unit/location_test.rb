require 'test_helper'

class LocationTest < ActiveSupport::TestCase
  
  test "has longitude latitude and altitude" do
    location = Location.new(:longitude => 9.0005, :latitude => 39.0001001, :altitude => 400.4)
    
    assert location.save
  end
  
  test "can calculate distance to another location" do
    location = Location.new(:longitude => 9.0005, :latitude => 39.0001001, :altitude => 400.4)
    other = Location.new(:longitude => 9.0006, :latitude => 39.0000991, :altitude => 400.4)
    
    assert_equal 5.375730282308196, location.distance_to(other) * 1000
  end
  
end
