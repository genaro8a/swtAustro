package ec.fin.baustro.isoutils;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.jpos.iso.*;
import java.io.IOException;
import java.util.Random;
@Slf4j
@ApplicationScoped
public class Procesador implements ISORequestListener {
    Random rdmAprb = new Random();
    @Override
    public boolean process(ISOSource isoSource, ISOMsg isoMsg) {
        try {
            log.info("trama a procesar : {}",ISOUtil.byte2hex(isoMsg.pack()));
            ISOMsg response;
            if (isoMsg.getMTI().compareTo("0800") == 0) {
                isoMsg.setResponseMTI();
                isoSource.send(isoMsg);
                return true;
            } else if (isoMsg.getMTI().compareTo("0200") == 0) {
                response = procesarAustro(isoMsg);
                response.setPackager((ISOBasePackager) isoMsg.getPackager());
                isoSource.send(response);
                log.info("trama respuesta : {}",ISOUtil.byte2hex( response.pack()));

            }
        } catch (ISOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private ISOMsg procesarAustro(ISOMsg isoMsg) {
        String cod = "";
        try {

            if (isoMsg.getMTI().compareTo("0200") == 0) {
                String terminal = isoMsg.getString(41);
                isoMsg.setResponseMTI();
                if (isoMsg.hasField(3)) {
                    isoMsg.set(3, isoMsg.getString(3));
                    cod = isoMsg.getString(3);
                }
                isoMsg.set(4, isoMsg.getString(4));
                if (isoMsg.hasField(4)) {
                    isoMsg.set(4, isoMsg.getString(4));
                }
                if (isoMsg.hasField(5)) {
                    isoMsg.set(5, isoMsg.getString(5));
                }
                if (isoMsg.hasField(11)) {
                    isoMsg.set(11, isoMsg.getString(11));
                }
                if (isoMsg.hasField(12)) {
                    isoMsg.set(12, isoMsg.getString(12));
                }
                if (isoMsg.hasField(13)) {
                    isoMsg.set(13, isoMsg.getString(13));
                }
                if (isoMsg.hasField(22)) {
                    isoMsg.set(22, isoMsg.getString(22));
                }
                isoMsg.set(11, isoMsg.getString(11));
                isoMsg.set(15, "0203");
                String aprobacion = ISOUtil.padleft(String.valueOf(this.rdmAprb.nextInt(50000)), 6, '0');
                isoMsg.set(37, ISOUtil.padleft(aprobacion, 12, '0'));
                String campo39 = "00";
                if (campo39.compareTo("00") == 0) {
                    isoMsg.set(38, aprobacion);
                }
                isoMsg.set(39, campo39);
                isoMsg.set(41, terminal);
            }else if (isoMsg.getMTI().compareTo("0100") == 0) {
                String terminal = isoMsg.getString(41);
                isoMsg.setResponseMTI();
                if (isoMsg.hasField(3)) {
                    isoMsg.set(3, isoMsg.getString(3));
                    cod = isoMsg.getString(3);
                }
                isoMsg.set(4, isoMsg.getString(4));
                if (isoMsg.hasField(4)) {
                    isoMsg.set(4, isoMsg.getString(4));
                }
                if (isoMsg.hasField(5)) {
                    isoMsg.set(5, isoMsg.getString(5));
                }
                if (isoMsg.hasField(11)) {
                    isoMsg.set(11, isoMsg.getString(11));
                }
                if (isoMsg.hasField(12)) {
                    isoMsg.set(12, isoMsg.getString(12));
                }
                if (isoMsg.hasField(13)) {
                    isoMsg.set(13, isoMsg.getString(13));
                }
                if (isoMsg.hasField(22)) {
                    isoMsg.set(22, isoMsg.getString(22));
                }
                isoMsg.set(11, isoMsg.getString(11));
                isoMsg.set(15, "0203");
                String aprobacion = ISOUtil.padleft(String.valueOf(this.rdmAprb.nextInt(50000)), 6, '0');
                isoMsg.set(37, ISOUtil.padleft(aprobacion, 12, '0'));
                String campo39 = "00";
                if (campo39.compareTo("00") == 0) {
                    isoMsg.set(38, aprobacion);
                }
                isoMsg.set(39, campo39);
                isoMsg.set(41, terminal);
            }
        } catch (ISOException e) {
            throw new RuntimeException(e);
        }
        return isoMsg;
    }
}
