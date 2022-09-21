package in.dudeapp.common.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author nandhan, Created on 26/07/22
 */
public class IDGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(
            SharedSessionContractImplementor sharedSessionContractImplementor,
            Object o) throws HibernateException {
        String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMdd"));
        String suffix = UUID.randomUUID().toString().replace("-","");
        return prefix+suffix;
    }
}
