class CreateBombs < ActiveRecord::Migration
  def self.up
    create_table :bombs do |t|

      t.timestamps
    end
  end

  def self.down
    drop_table :bombs
  end
end
