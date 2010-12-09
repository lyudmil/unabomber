class AddDeviceIdToPlayers < ActiveRecord::Migration
  def self.up
    add_column :players, :device_id, :string
  end

  def self.down
    remove_column :players, :device_id
  end
end
