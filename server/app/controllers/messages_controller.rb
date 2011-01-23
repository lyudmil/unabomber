class MessagesController < ApplicationController
  
  def index
    recepient = Player.find_by_device_id(params[:device_id])
    messages = Message.where(:recepient_id => recepient.id)
    
    render :text => messages.to_json
  end

  def create
    sender = Player.find_by_device_id(params[:device_id])
    recepient = Player.find(params[:recepient_id])
    content = params[:content]
    
    @message = Message.create(:sender => sender, :recepient => recepient, :content => content)
    
    redirect_to @message 
  end
  
  def show
    @message = Message.find(params[:id])
    render :text => @message.to_json
  end
  
end
