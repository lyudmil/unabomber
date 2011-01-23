require 'test_helper'

class StatusControllerTest < ActionController::TestCase
  
  def setup
    @player = flexmock(:model, Player)
    flexmock(Player).should_receive(:find_by_device_id).with('333').and_return(@player)
  end
  
  test "can tell if a player has been killed" do
    flexmock(Player).should_receive(:find_by_device_id).with('111').and_return(nil)
    flexmock(Player).should_receive(:where).with(:role => :citizen)
    
    get :index, :device_id => '111'
    
    assert_equal 'finished-killed', @response.body
  end
  
  test "can tell if a citizen has won" do
    @player.should_receive(:role).and_return(:citizen)
    flexmock(Player).should_receive(:where).with(:role => :citizen)
    no_unabombers
    
    get :index, :device_id => '333'
    
    assert_equal 'finished-win', @response.body    
  end
  
  test "can tell if a cop has won" do
    @player.should_receive(:role).and_return(:policeman)
    flexmock(Player).should_receive(:where).with(:role => :citizen)
    no_unabombers
    
    get :index, :device_id => '333'
    
    assert_equal 'finished-win', @response.body
  end
  
  test "can tell if a cop has lost" do
    @player.should_receive(:role).and_return(:policeman)
    no_citizens
    flexmock(Player).should_receive(:where).with(:role => :unabomber).and_return([flexmock(:model, Player)])
    
    get :index, :device_id => '333'
    
    assert_equal 'finished-lose', @response.body
  end
  
  test "can tell if a unabomber has won" do
    @player.should_receive(:role).and_return(:unabomber)
    no_citizens
    
    get :index, :device_id => '333'
    
    assert_equal 'finished-win', @response.body
  end
  
  test "can tell if a game is still in progress" do
    @player.should_receive(:role).and_return(:citizen)
    flexmock(Player).should_receive(:where).with(:role => :citizen).and_return([@player, flexmock(:model, Player)])
    flexmock(Player).should_receive(:where).with(:role => :unabomber).and_return([flexmock(:model, Player)])
    
    get :index, :device_id => '333'
    
    assert_equal 'started', @response.body
  end
  
  private
  
  def no_unabombers
    flexmock(Player).should_receive(:where).with(:role => :unabomber).and_return([])
  end
  
  def no_citizens
    flexmock(Player).should_receive(:where).with(:role => :citizen).and_return([])
  end
  
end
