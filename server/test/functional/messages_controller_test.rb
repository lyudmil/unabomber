require 'test_helper'

class MessagesControllerTest < ActionController::TestCase
  
  test "creates a new message" do
    sender, recepient = flexmock(:model, Player), flexmock(:model, Player)
    flexmock(Player).should_receive(:find_by_device_id).with('111').and_return(sender)
    flexmock(Player).should_receive(:find).with(3).and_return(recepient)
    
    message = flexmock(:model, Message)
    flexmock(Message).should_receive(:create).with(:sender => sender, :recepient => recepient, :content => 'Hello').once.and_return(message)
    
    put :create, :device_id => '111', :recepient_id => 3, :content => 'Hello'
    
    assert_redirected_to message
  end
  
  test "shows a message" do
    message = flexmock(:model, Message, :to_json => nil)
    flexmock(Message).should_receive(:find).with(1).and_return(message)
    
    get :show, :id => 1
    assert_equal message, assigns(:message)
  end
  
  test "shows all messages for a given recepient" do
    recepient = flexmock(:model, Player)
    flexmock(Player).should_receive(:find_by_device_id).with('111').and_return(recepient)
    
    m1, m2, m3 = flexmock(:model, Message), flexmock(:model, Message), flexmock(:model, Message)
    messages = [m1, m2, m3]
    flexmock(messages).should_receive(:to_json).once.and_return(nil)
    flexmock(Message).should_receive(:where).with(:recepient_id => recepient.id).and_return(messages)

    get :index, :device_id => '111'
  end
  
end
