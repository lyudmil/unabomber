class DetermineIfPlayersAreKilled < ActiveRecord::Migration
  def self.up
    add_column :players, :killed, :boolean, :default => false
  end

  def self.down
    remove_column :players, :killed
  end
end
