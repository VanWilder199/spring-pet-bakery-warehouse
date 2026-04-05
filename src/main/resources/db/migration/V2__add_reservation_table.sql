CREATE TABLE reservation (
    id BIGSERIAL PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    reserved_until TIMESTAMP NOT NULL
);

ALTER TABLE reservation
    ADD CONSTRAINT  uq_reservation_order_id UNIQUE (order_id)