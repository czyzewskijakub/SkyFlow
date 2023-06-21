-- Create trigger that removes items from carts after 30 minutes
CREATE OR REPLACE FUNCTION delete_record_after_30_minutes()
RETURNS TRIGGER AS $$
BEGIN
    IF (NEW.addition_date < NOW() - INTERVAL '30 minutes') THEN
DELETE FROM cart WHERE cart_record_id = NEW.cart_record_id;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_and_delete_trigger
    AFTER INSERT OR UPDATE ON cart
                        FOR EACH ROW
                        EXECUTE FUNCTION delete_record_after_30_minutes();

-- CREATE statuses table
CREATE TABLE statuses (
  status_id SERIAL PRIMARY KEY,
  status VARCHAR(50) NOT NULL
);

INSERT INTO statuses (status_id, status)
VALUES (0, 'RESERVED'), (1, 'VALID'), (2, 'USED'), (3, 'CANCELLED');

-- Default user account
insert into users values (1, 'example@gmail.com', 'John', true,'Smith', '$2a$10$NB5uxsLNyzzoqJCU1Cn47OnJWfTOYjnqnD540Ri8JtJLuzEGSaEy2', 'https://pl.pinterest.com/pin/327848047887112192/');