require 'test_helper'

class BombTest < ActiveSupport::TestCase

	def setup
		@bomb = Bomb.new
		assert @bomb.save

		@location = Location.new(:latitude => 9.0, :longitude => 49.0, :altitude => 500)
		assert @location.save

		@player = Player.new(:device_id => "abc123", :location => @location)
		assert @player.save
	end

	test "has a location" do
		@bomb.location = @location

		assert @bomb.save
		assert_equal @location, @bomb.location
	end

	test "has a player" do
		@bomb.player = @player
		assert @bomb.save

		assert_equal @player, @bomb.player
		assert @player.bombs.include?(@bomb)
	end

end
