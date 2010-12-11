class AssociatePlayersAndLocations < ActiveRecord::Migration
  def self.up
    add_column :locations, :player_id, :integer
  end

  def self.down
    remove_column :locations, :player_id
  end
end
